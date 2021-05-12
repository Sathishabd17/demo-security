//package SpringSecurityConfig;
//
//import com.example.demo.config.JpaConfig;
//import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
//
//public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
//    @Override
//    protected Class<?>[] getRootConfigClasses() {
//        return new Class[] { JpaConfig.class, WebSecurityConfig.class };
//    }
//
//    @Override
//    protected Class<?>[] getServletConfigClasses() {
//        return new Class[] { WebMvcConfig.class };
//    }
//
//    @Override
//    protected String[] getServletMappings() {
//        return new String[] { "/" };
//    }
//}
