package org.wower.hmc.ebayhomecode.bean;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class User implements Serializable {

    Long userId;
    List<String> endpoint;
}
