/*
 * <<
 *  Davinci
 *  ==
 *  Copyright (C) 2016 - 2019 EDP
 *  ==
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *        http://www.apache.org/licenses/LICENSE-2.0
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  >>
 *
 */

package com.zyplayer.doc.manage.web.param;

/**
 * 域账号用户信息
 *
 * @author 暮光：城中城
 * @since 2021年8月2日
 */
public class LdapPerson {
	
	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 用户名
	 */
	private String sAMAccountName;
	
	
	/**
	 * 邮箱
	 */
	private String email;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getsAMAccountName() {
		return sAMAccountName;
	}
	
	public void setsAMAccountName(String sAMAccountName) {
		this.sAMAccountName = sAMAccountName;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public LdapPerson(String name, String sAMAccountName, String email) {
		this.name = name;
		this.sAMAccountName = sAMAccountName;
		this.email = email;
	}
	
	public LdapPerson() {
	}
	
}
