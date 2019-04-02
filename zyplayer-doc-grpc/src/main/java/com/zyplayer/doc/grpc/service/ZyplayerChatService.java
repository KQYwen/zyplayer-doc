//package com.zyplayer.doc.grpc.service;
//
//
//import com.google.protobuf.ByteString;
//import com.nxest.grpc.server.GrpcService;
//import com.zyplayer.doc.grpc.proto.BaseMsg;
//import com.zyplayer.doc.grpc.proto.ChatMsg;
//import com.zyplayer.doc.grpc.proto.User;
//import com.zyplayer.doc.grpc.proto.ZyplayerChatGrpc;
//import io.grpc.stub.StreamObserver;
//
///**
// * grpc服务测试类，需要mvn compile项目
// * @author 暮光：城中城
// * @since 2019年3月31日
// */
//@GrpcService
//public class ZyplayerChatService extends ZyplayerChatGrpc.ZyplayerChatImplBase {
//
//	@Override
//	public void sendText(ChatMsg request, StreamObserver<ChatMsg> responseObserver) {
//		User user = null;
//		try {
//			user = User.newBuilder().setCookies(ByteString.copyFrom("xxx", "utf-8")).build();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		BaseMsg baseMsg = BaseMsg.newBuilder().setCmd(1).setUser(user).build();
//		ChatMsg reply = ChatMsg.newBuilder().setToken("sendText").setIP("xx.xx.xx.xx").setBaseMsg(baseMsg).build();
//		responseObserver.onNext(reply);
//		responseObserver.onCompleted();
//	}
//
//	@Override
//	public void sendImage(ChatMsg request, StreamObserver<ChatMsg> responseObserver) {
//		ChatMsg reply = ChatMsg.newBuilder().setToken("sendImage").setIP("xx.xx.xx.xx").build();
//		responseObserver.onNext(reply);
//		responseObserver.onCompleted();
//	}
//}
