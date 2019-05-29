//package com.zyplayer.doc.manage.framework.config.security;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.zyplayer.doc.data.config.security.DocUserDetails;
//import com.zyplayer.doc.data.repository.manage.entity.AuthInfo;
//import com.zyplayer.doc.data.repository.manage.entity.UserAuth;
//import com.zyplayer.doc.data.repository.manage.entity.UserInfo;
//import com.zyplayer.doc.data.service.manage.AuthInfoService;
//import com.zyplayer.doc.data.service.manage.UserAuthService;
//import com.zyplayer.doc.data.service.manage.UserInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class DocDetailsServiceImpl implements UserDetailsService {
//
//	@Autowired
//	private UserInfoService userInfoService;
//	@Autowired
//	private UserAuthService userAuthService;
//	@Autowired
//	private AuthInfoService authInfoService;
//
//	@Override
//	public UserDetails loadUserByUsername(String userNo) throws UsernameNotFoundException {
//		QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
//		queryWrapper.eq("user_no", userNo);
//		UserInfo userInfo = userInfoService.getOne(queryWrapper);
//		if (userInfo == null) {
//			throw new UsernameNotFoundException("用户名'" + userNo + "'没有找到！");
//		}
//		QueryWrapper<UserAuth> authWrapper = new QueryWrapper<>();
//		authWrapper.eq("user_id", userInfo.getId()).eq("del_flag", "0");
//		List<UserAuth> userAuthList = userAuthService.list(authWrapper);
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		if (userAuthList != null && userAuthList.size() > 0) {
//			List<Long> authIdList = userAuthList.stream().map(UserAuth::getAuthId).collect(Collectors.toList());
//			Collection<AuthInfo> authInfoList = authInfoService.listByIds(authIdList);
//			authInfoList.forEach(val -> {
//				authorities.add(new SimpleGrantedAuthority(val.getAuthName()));
//			});
//		}
//		//String pwdMd5 = DigestUtils.md5DigestAsHex(userInfo.getPassword().getBytes());
//		DocUserDetails userDetails = new DocUserDetails(userInfo.getId(), userInfo.getUserName(), userInfo.getPassword(), true, authorities);
//		return userDetails;
//	}
//
//}
