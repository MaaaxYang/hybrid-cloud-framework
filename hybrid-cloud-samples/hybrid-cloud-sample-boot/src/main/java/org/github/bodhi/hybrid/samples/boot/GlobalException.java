package org.github.bodhi.hybrid.samples.boot;

/**
 * @program: hybrid-cloud-framework
 * @description:
 * @author: Maxxx.Yg
 * @create: 2019-03-26 18:21
 **/
//@RestControllerAdvice
//public class GlobalException {
//
//    private final static Logger logger = LoggerFactory.getLogger(GlobalException.class);
//
//    @ResponseStatus(HttpStatus.OK)
//    @ExceptionHandler({Exception.class})
//    public ApiResult globalExceptionHandler(Exception ex) {
//
//        LogEntity logEntity = LogHolder.get();
//        logEntity.setStatus(StandardCode.ERROR.getCode());
//        logEntity.setResult("BizError");
//        logEntity.setMsg(ex.getMessage());
//        logEntity.setLevel(ExceptionLevel.PROFOUND);
//
//        logger.error("UnknownException-Log", ex);
//        return ApiResult.error(StandardCode.ERROR.getCode(), ex.getMessage());
//    }
//
//
//}
