package org.koreait.repositories;

import com.querydsl.core.BooleanBuilder;
import org.koreait.entities.file.FileEntity;
import org.koreait.entities.file.QFileEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

public interface FileRepository extends JpaRepository<FileEntity,Long>, QuerydslPredicateExecutor<FileEntity> {

    /**
     * 파일 정보 조회
     * @param gid
     * @param location
     * @param mode : all - 완료, 미완료 파일 모두 조회, done - 완료 파일, undone - 미완료 파일
     * @return
     */
    default List<FileEntity> getFiles(String gid, String location, String mode){
        QFileEntity fileEntity = QFileEntity.fileEntity;

        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileEntity.gId.eq(gid));

        if(location != null && !location.isBlank()){
            builder.and(fileEntity.location.eq(location));
        }

        if(mode.equals("done")) builder.and(fileEntity.done.eq(true));  //작업 완료 파일
        else if (mode.equals("undone")) builder.and(fileEntity.done.eq(false)); //미완료 파일

        List<FileEntity> items = (List<FileEntity>) findAll(builder, Sort.by(asc("createdAt")));

        return items;
    }

    /**
     * 완료, 미완료 파일 모두 조회
     * @param gid
     * @param location
     * @return
     */
    default List<FileEntity> getFiles(String gid, String location){
        return getFiles(gid, location, "all");
    }

    default List<FileEntity> getFiles(String gid){
        return getFiles(gid, null);
    }

    /**
     * 업로드 완료된 파일만 조회
     * @param gid
     * @param location
     * @return
     */
    default List<FileEntity> getFilesDone(String gid, String location){
        return getFiles(gid, location, "done");
    }
    default List<FileEntity> getFilesDone(String gid){
        return getFiles(gid, null);
    }

    /**
     *  작업 완료 처리
     * @param gid
     */
    default void processDone(String gid) {
        List<FileEntity> items = getFiles(gid);
        items.stream().forEach(item -> {item.setDone(true);});
        flush();
    }
}
