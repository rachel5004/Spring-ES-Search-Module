package dev.demo.search.common.util;

import lombok.Getter;
import java.io.Serializable;

@Getter
public class AuthContents implements Serializable {

    private String expire_time;

    private String member_no;

    private Long application_no;

}
