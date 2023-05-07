package todoapp.core.todos.application;

import java.util.List;
import todoapp.core.todos.domain.Todo;

/**
 * 할 일 검색기 인터페이스
 *
 * @author springrunner.kr@gmail.com
 */
public interface TodoFinder {

    /**
     * 등록된 모든 할 일 목록을 반환한다.
     * 할 일이 없으면 빈 목록을 반환한다.
     *
     * @return List<Todo> 개체
     */
    List<Todo> getAll();

}