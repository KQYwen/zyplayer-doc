package com.zyplayer.doc.grpc.service;


import com.nxest.grpc.server.GrpcService;
import com.zyplayer.doc.grpc.proto.ChatMsg;
import com.zyplayer.doc.grpc.proto.ZyplayerChatGrpc;
import io.grpc.stub.StreamObserver;

@GrpcService
public class ZyplayerChatService extends ZyplayerChatGrpc.ZyplayerChatImplBase {
	
	@Override
	public void sendText(ChatMsg request, StreamObserver<ChatMsg> responseObserver) {
		ChatMsg reply = ChatMsg.newBuilder().setToken("sendMsg").build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
	
	@Override
	public void sendImage(ChatMsg request, StreamObserver<ChatMsg> responseObserver) {
		ChatMsg reply = ChatMsg.newBuilder().setToken("sendImage").build();
		responseObserver.onNext(reply);
		responseObserver.onCompleted();
	}
}
