可保持线程日志统一输出，多线程不混乱，比如多个请求时，每个请求的日志都是同时输出，而不是掺和成你一行我一行。但是可配置性没那么灵活，如果需要，请自行完善
日志文件目录格式为
info.log
2019-07/info_2019-07-05_150518-849.log
       /info_2019-07-05_150518-849.log
2019-08/info_2019-08-05_150518-849.log
       /info_2019-08-05_150518-849.log


把代码直接放入项目中

核心功能 把同一线程的日志，同时写入日志文件，默认是一条日志一次写入
但是要手动开启，拦截器的例子

@Component
public class CommonIntercetpor implements HandlerInterceptor{

    private static Logger logger = LoggerFactory.getLogger(CommonIntercetpor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        LogUtil.autoFlush.set(false);
        logger.info(BaseController.getClientIP(request)+" "+request.getServletPath()+JSON.toJSONString(request.getParameterMap()));
        return true;
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        LogUtil.flushLine();
    }
}

如果有异步任务，也要使用aop进行开启多条日志同时写入文件的功能

   @Component
   @Aspect
   public class AsyncAop {


   	@Pointcut("execution(public void com.YibuJob.*(..))")
       public  void aspect() {}

   	@Before("aspect()")
   	public void before(JoinPoint joinpoint)  {
   		LogUtil.autoFlush.set(false);
   	}

   	@After("aspect()")
   	public void after(JoinPoint joinpoint)  {
   		LogUtil.flushLine();
   	}

   }
