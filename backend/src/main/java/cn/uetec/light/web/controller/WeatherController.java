package cn.uetec.light.web.controller;

import cn.uetec.light.common.domain.LightConstant;
import cn.uetec.light.common.domain.LightResponse;
import cn.uetec.light.common.exception.LightException;
import cn.uetec.light.common.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@Slf4j
@Validated
@RestController
@RequestMapping("weather")
public class WeatherController {

    @GetMapping
    @RequiresPermissions("weather:view")
    public LightResponse queryWeather(@NotBlank(message = "{required}") String areaId) throws LightException {
        try {
            String data = HttpUtil.sendPost(LightConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return new LightResponse().data(data);
        } catch (Exception e) {
            String message = "天气查询失败";
            log.error(message, e);
            throw new LightException(message);
        }
    }
}
