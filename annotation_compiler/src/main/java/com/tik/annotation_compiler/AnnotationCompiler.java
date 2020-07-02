package com.tik.annotation_compiler;

import com.google.auto.service.AutoService;
import com.tik.annotation.Route;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
//@SupportedAnnotationTypes("com.tik.annotation.Route")
//@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class AnnotationCompiler extends AbstractProcessor {
    Filer filer;

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new HashSet<>();
        types.add(Route.class.getCanonicalName());
        return types;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Route.class);
        Map<String, String> map = new HashMap<>();
        for (Element element : elements) {
            TypeElement typeElement = (TypeElement) element;
            //获取Activity上的Route注解
            Route annotation = typeElement.getAnnotation(Route.class);
            //获取注解配置的值
            String path = annotation.value();
            //获取Activity的包名加类名
            Name activityName = typeElement.getQualifiedName();
            map.put(path, activityName + ".class");
        }
        if (map.size() > 0) {
            Writer writer = null;
            String activityName = "ARouterUtil" + System.currentTimeMillis();
            try {
                JavaFileObject javaFileObject = filer.createSourceFile("com.tik.arouter.util."+activityName);
                writer = javaFileObject.openWriter();
                StringBuffer sb = new StringBuffer();
                sb.append("package com.tik.arouter.util;\n");
                sb.append("import com.tik.arouter.ARouter;\n" +
                        "import com.tik.arouter.IRouter;\n" +
                        "\n" +
                        "public class " + activityName + " implements IRouter {\n" +
                        "    @Override\n" +
                        "    public void putActivity() {\n");
                Iterator<String> iterator = map.keySet().iterator();
                while (iterator.hasNext()) {
                    String key = iterator.next();
                    String className = map.get(key);
                    sb.append("ARouter.getInstance().addActivity(\"" + key + "\"," +
                            className + ");\n");
                }
                sb.append("\n}\n}");
                writer.write(sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (writer != null) {
                    try {
                        writer.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }
}
