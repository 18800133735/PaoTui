package com.bawei.paotui.utils.jiami;

import java.util.UUID;

public class UUIDGenerate {
	
	/**  
     * 生成32位编码  
     * @return string  
     */    
    public String getUUID(){    
        String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");    
        return uuid;    
    }   

}
