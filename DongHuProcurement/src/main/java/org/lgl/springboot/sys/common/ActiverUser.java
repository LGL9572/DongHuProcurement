package org.lgl.springboot.sys.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.lgl.springboot.sys.domain.User;

import java.util.List;

/**
 * User对应的实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActiverUser {
    private User user;
    private List<String> roles;
    private List<String> permissions;
}
