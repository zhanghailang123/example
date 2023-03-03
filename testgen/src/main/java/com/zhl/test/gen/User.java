package com.zhl.test.gen;

import com.zhl.gen.vo.GenDO;
import com.zhl.gen.vo.GenGatewayImpl;

/**
 * @author hailang.zhang
 * @since 2023-03-02
 */
@GenDO(pkgName = "com.zhl.test.gen.zzz")
@GenGatewayImpl(pkgName = "com.zhl.test.gen.zzz")
public class User {

    private String username;
}