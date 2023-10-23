package org.koreait.services.post;

import lombok.RequiredArgsConstructor;
import org.koreait.commons.utils.MemberUtil;
import org.koreait.entities.file.FileEntity;
import org.koreait.entities.post.Post;
import org.koreait.exceptions.post.PostNotExistsException;
import org.koreait.repositories.PostRepository;
import org.koreait.services.board.BoardConfigInfoService;
import org.koreait.services.flie.FileInfoService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostInfoService {

    private final PostRepository postRepository;
    private final BoardConfigInfoService boardConfigInfoService;
    private final FileInfoService fileInfoService;
    private final MemberUtil memberUtil;


    public Post get(Long id){
        return get(id, "view");
    }

    public Post get(Long id, String location){
        Post post = postRepository.findById(id).orElseThrow(PostNotExistsException::new);

        String gid = post.getGid();
        /** 에디터 첨부파일 조회 */
        List<FileEntity> attachEditors = fileInfoService.gets(gid, "editor");
        post.setAttachEditors(attachEditors);

        /** 일반 첨부 파일 조회 */
        List<FileEntity> attachFiles = fileInfoService.gets(gid, "attach");
        post.setAttachFiles(attachFiles);

        //게시판 설정 조회 + 접근 권한 체크
        boardConfigInfoService.get(post.getBoard().getBId(), location);

        return post;
    }
}
