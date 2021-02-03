package kakao.itstudy.adpterview

import java.io.Serializable

//VO(Variable Object, DTO(Data Transfer Object))
//사용자의 데이터를 표현하기 위한 클래스
//필요한 변수 선언, 접근자 메소드(getter,setter), toString(디버깅)
//상황에 따라 Serializable(직렬화-객체 단위 전송 가능) 인터페이스 implements
class VO : Serializable {
    //이미지의 아이디를 저장할 변수
    var icon = 0

    //출력할 텍스트를 저장할 변수
    var name: String? = null

    override fun toString(): String {
        return "VO [icon=$icon, name=$name]"
    }
}
