package com.zhl.test.gen;

import com.zhl.gen.core.GenDO;
import com.zhl.gen.core.GenDOConverter;
import com.zhl.gen.core.GenGateway;
import com.zhl.gen.core.GenGatewayImpl;

/**
 * @author hailang.zhang
 * @since 2023-03-02
 */
@GenDO(pkgName = "com.zhl.test.gen.zzz")
@GenGateway(pkgName = "com.zhl.test.gen.zzz")
@GenGatewayImpl(pkgName = "com.zhl.test.gen.zzz")
@GenDOConverter(pkgName = "com.zhl.test.gen.zzz")
public class User {

    private String username;
    private String age;
}