package kakao.itstudy.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.auth.model.Prompt
import com.kakao.sdk.user.UserApiClient
import kotlinx.android.synthetic.main.activity_kakao_login.*

class KakaoLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kakao_login)

        // 로그인 공통 callback 구성
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                Log.e("카카오 로그인", "로그인 실패", error)
            } else if (token != null) {
                Log.i("카카오 로그인", "로그인 성공 ${token.accessToken}")

                // 토큰 정보 보기
                UserApiClient.instance.accessTokenInfo { tokenInfo, error ->
                    if (error != null) {
                        Log.e("토큰", "토큰 정보 보기 실패", error)
                    } else if (tokenInfo != null) {
                        Log.i(
                            "토큰", "토큰 정보 보기 성공" +
                                    "\n회원번호: ${tokenInfo.id}" +
                                    "\n만료시간: ${tokenInfo.expiresIn} 초"
                        )
                    }
                }

                // 사용자 정보 요청 (기본)
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("사용자 정보", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        Log.i(
                            "사용자 정보", "사용자 정보 요청 성공" +
                                    "\n회원번호: ${user.id}" +
                                    "\n이메일: ${user.kakaoAccount?.email}" +
                                    "\n닉네임: ${user.kakaoAccount?.profile?.nickname}" +
                                    "\n프로필사진: ${user.kakaoAccount?.profile?.thumbnailImageUrl}"
                        )
                    }
                }

                // 사용자 정보 저장
                // 변경할 내용
                val properties = mapOf("nickname" to "${System.currentTimeMillis()}")

                UserApiClient.instance.updateProfile(properties) { error ->
                    if (error != null) {
                        Log.e("메시지", "사용자 정보 저장 실패", error)
                    } else {
                        Log.i("메시지", "사용자 정보 저장 성공")
                    }
                }

                // 사용자 정보 요청 (추가 동의)

                // 사용자가 로그인 시 제3자 정보제공에 동의하지 않은 개인정보 항목 중 어떤 정보가 반드시 필요한 시나리오에 진입한다면
                // 다음과 같이 추가 동의를 받고 해당 정보를 획득할 수 있습니다.

                //  * 주의: 선택 동의항목은 사용자가 거부하더라도 서비스 이용에 지장이 없어야 합니다.

                // 이메일 필수 시나리오 예제
                UserApiClient.instance.me { user, error ->
                    if (error != null) {
                        Log.e("메시지", "사용자 정보 요청 실패", error)
                    } else if (user != null) {
                        if (user.kakaoAccount?.email != null) {
                            Log.i("메시지", "이메일: ${user.kakaoAccount?.email}")
                        } else if (user.kakaoAccount?.emailNeedsAgreement == false) {
                            Log.e("메시지", "사용자 계정에 이메일 없음. 꼭 필요하다면 동의항목 설정에서 수집 기능을 활성화 해보세요.")
                        } else if (user.kakaoAccount?.emailNeedsAgreement == true) {
                            Log.d("메시지", "사용자에게 이메일 제공 동의를 받아야 합니다.")

                            // 사용자에게 이메일 제공 동의 요청
                            val scopes = listOf("account_email")
                            LoginClient.instance.loginWithNewScopes(this, scopes) { token, error ->
                                if (error != null) {
                                    Log.e("메시지", "이메일 제공 동의 실패", error)
                                } else {
                                    Log.d("메시지", "allowed scopes: ${token!!.scopes}")

                                    // 사용자 정보 재요청
                                    UserApiClient.instance.me { user, error ->
                                        if (error != null) {
                                            Log.e("메시지", "사용자 정보 요청 실패", error)
                                        } else if (user != null) {
                                            Log.i("메시지", "이메일: ${user.kakaoAccount?.email}")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        kakaoLoginBtn.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (LoginClient.instance.isKakaoTalkLoginAvailable(this)) {
                // 연결 끊기
                UserApiClient.instance.unlink { error ->
                    if (error != null) {
                        Log.e("토큰 삭제", "연결 끊기 실패", error)

                        LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
                    } else {
                        Log.i("토큰 삭제 ", "연결 끊기 성공. SDK에서 토큰 삭제 됨")
                        LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
                    }
                }
            } else {
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }
    }
}