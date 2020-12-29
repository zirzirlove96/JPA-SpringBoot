package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {

    @NotEmpty(message="회원 이름은 필수 입니다.")
    private String name;

    private String city;
    private String street;
<<<<<<< HEAD
    private String zipCode;
=======
    private String zipcode;
>>>>>>> 176fabe4c53b77de93ebeaa83c7854f353b0d68b
}
