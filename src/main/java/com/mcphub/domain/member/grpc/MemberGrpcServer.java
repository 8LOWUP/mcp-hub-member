package com.mcphub.domain.member.grpc;


import com.mcphub.domain.member.entity.Member;
import com.mcphub.domain.member.repository.Jpa.MemberRepository;
import com.mcphub.global.common.exception.RestApiException;
import com.mcphub.global.common.exception.code.status.GlobalErrorStatus;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@GrpcService
@RequiredArgsConstructor
public class MemberGrpcServer extends MemberServiceGrpc.MemberServiceImplBase {
    private final MemberRepository memberRepository;

    @Override
    @Transactional(readOnly = true)
    public void getMemberInfo(GetMemberInfoRequest request,
                              StreamObserver<MemberResponse> responseObserver) {
        // 서버에서 userName 가져오는 로직
        Long userId = Long.valueOf(request.getUserId());
        Member member = memberRepository.findById(userId)
                                        .orElseThrow(() -> new RestApiException(GlobalErrorStatus._NOT_FOUND));
        String userName = member.getNickname();
        MemberResponse resp = MemberResponse.newBuilder()
                .setUserId(request.getUserId())
                .setName(userName)
                .build();
        responseObserver.onNext(resp);
        responseObserver.onCompleted();
    }
}
