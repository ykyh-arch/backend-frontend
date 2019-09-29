package cn.uetec.light.web.controller;

import cn.uetec.light.common.domain.LightConstant;
import cn.uetec.light.common.domain.LightResponse;
import cn.uetec.light.common.exception.LightException;
import cn.uetec.light.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("movie")
public class MovieController {

    private String message;

    @GetMapping("hot")
    public LightResponse getMoiveHot() throws LightException {
        try {
            String data = HttpUtil.sendSSLPost(LightConstant.TIME_MOVIE_HOT_URL, "locationId=328");
            return new LightResponse().data(data);
        } catch (Exception e) {
            message = "获取热映影片信息失败";
            log.error(message, e);
            throw new LightException(message);
        }
    }

    @GetMapping("coming")
    public LightResponse getMovieComing() throws LightException {
        try {
            String data = HttpUtil.sendSSLPost(LightConstant.TIME_MOVIE_COMING_URL, "locationId=328");
            return new LightResponse().data(data);
        } catch (Exception e) {
            message = "获取即将上映影片信息失败";
            log.error(message, e);
            throw new LightException(message);
        }
    }

    @GetMapping("detail")
    public LightResponse getDetail(@NotBlank(message = "{required}") String id) throws LightException {
        try {
            String data = HttpUtil.sendSSLPost(LightConstant.TIME_MOVIE_DETAIL_URL, "locationId=328&movieId=" + id);
            return new LightResponse().data(data);
        } catch (Exception e) {
            message = "获取影片详情失败";
            log.error(message, e);
            throw new LightException(message);
        }
    }

    @GetMapping("comments")
    public LightResponse getComments(@NotBlank(message = "{required}") String id) throws LightException {
        try {
            String data = HttpUtil.sendSSLPost(LightConstant.TIME_MOVIE_COMMENTS_URL, "movieId=" + id);
            return new LightResponse().data(data);
        } catch (Exception e) {
            message = "获取影片评论失败";
            log.error(message, e);
            throw new LightException(message);
        }
    }
}
