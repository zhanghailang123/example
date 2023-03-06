package com.zhl.test.gen;

import com.zhl.gen.core.GenCmdExe;
import com.zhl.gen.core.GenConverter;
import com.zhl.gen.core.GenDO;
import com.zhl.gen.core.GenDOConverter;
import com.zhl.gen.core.GenGateway;
import com.zhl.gen.core.GenGatewayImpl;
import com.zhl.gen.core.GenQryExe;
import com.zhl.gen.core.GenService;
import com.zhl.gen.core.GenServiceImpl;

/**
 * @author hailang.zhang
 * @since 2023-03-02
 */
@GenDO(pkgName = "com.zhl.test.gen.zzz")
@GenGateway(pkgName = "com.zhl.test.gen.zzz")
@GenGatewayImpl(pkgName = "com.zhl.test.gen.zzz")
@GenDOConverter(pkgName = "com.zhl.test.gen.zzz")
@GenCmdExe(pkgName = "com.zhl.test.gen.zzz")
@GenQryExe(pkgName = "com.zhl.test.gen.zzz")
@GenConverter(pkgName = "com.zhl.test.gen.zzz")
@GenService(pkgName = "com.zhl.test.gen.zzz")
@GenServiceImpl(pkgName = "com.zhl.test.gen.zzz")
public class User {

    private String username;
    private String age;
}