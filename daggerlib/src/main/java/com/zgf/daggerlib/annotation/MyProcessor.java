package com.zgf.daggerlib.annotation;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.zgf.daggerlib.annotation.ApiAnnotation")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class MyProcessor extends AbstractProcessor {

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        System.out.println("========init===========");
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        System.out.println("========process===========");

        for (Element annotatedElement : roundEnvironment.getElementsAnnotatedWith(ApiAnnotation.class)) {
            analysisAnnotated(annotatedElement);
        }
        return false;
    }

    /**
     * 生成java文件
     *
     * @param classElement 注解
     */
    private void analysisAnnotated(Element classElement) {
        ApiAnnotation annotation = classElement.getAnnotation(ApiAnnotation.class);
        String author = annotation.author();
        String date = annotation.date();
        int version = annotation.version();
        String newClassName = "MainActivity$$ApiAnnotation";

        StringBuilder builder = new StringBuilder()
                .append("package com.zgf.annotationtest.auto;\n\n")
                .append("public class ")
                .append(newClassName)
                .append(" {\n\n") // open class
                .append("\tpublic String getMessage() {\n") // open method
                .append("\t\treturn \"");

        // this is appending to the return statement
        builder.append("author=")
                .append(author)
                .append(", data=")
                .append(date)
                .append(", version=")
                .append(version)
                .append(", name=")
                .append(newClassName)
                .append(" !\\n");

        builder.append("\";\n") // end returne
                .append("\t}\n") // close method
                .append("}\n"); // close class


        try { // write the file
            JavaFileObject source = processingEnv.getFiler().createSourceFile("com.zgf.annotationtest.auto." + newClassName);
            Writer writer = source.openWriter();
            writer.write(builder.toString());
            writer.flush();
            writer.close();
        } catch (IOException e) {
            // Note: calling e.printStackTrace() will print IO errors
            // that occur from the file already existing after its first run, this is normal
        }

    }
}
