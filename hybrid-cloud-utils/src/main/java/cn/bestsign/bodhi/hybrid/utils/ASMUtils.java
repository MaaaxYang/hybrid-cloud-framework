package org.github.bodhi.hybrid.utils;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.LocalVariableNode;
import org.objectweb.asm.tree.MethodNode;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ASMUtils {

    public static List<String> getMethodParamNames(Class<?> clazz, Method m) throws IOException {
        try (InputStream in = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class")) {
            return getMethodParamNames(in,m);
        }

    }
    public static List<String> getMethodParamNames(InputStream in, Method m) throws IOException {
        try (InputStream ins=in) {
            return getParamNames(ins,
                    new MethodMetadata(m.getName(),Type.getMethodDescriptor(m), m.getParameterTypes().length));
        }

    }

    public static List<String> getConstructorParamNames(Class<?> clazz, Constructor<?> constructor) {
        try (InputStream in = clazz.getResourceAsStream("/" + clazz.getName().replace('.', '/') + ".class")) {
            return getConstructorParamNames(in, constructor);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    public static List<String> getConstructorParamNames(InputStream ins, Constructor<?> constructor) {
        try (InputStream in = ins) {
            return getParamNames(in, new MethodMetadata(constructor.getName(),Type.getConstructorDescriptor(constructor),
                    constructor.getParameterTypes().length));
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    /**
     * 获取参数名列表辅助方法
     *
     * @param in
     * @param m
     * @return
     * @throws IOException
     */
    private static List<String> getParamNames(InputStream in, MethodMetadata m) throws IOException {
        ClassReader cr = new ClassReader(in);
        ClassNode cn = new ClassNode();

        // normal suggest use EXPAND_FRAMES flag
        cr.accept(cn, ClassReader.EXPAND_FRAMES);

        // use ASM tree request
        List<MethodNode> methods = cn.methods;

        List<String> list = new ArrayList<String>();
        for (int i = 0; i < methods.size(); ++i) {
            List<LocalVariable> varNames = new ArrayList<LocalVariable>();
            MethodNode method = methods.get(i);
            // check method signature
            if (method.desc.equals(m.desc)&&method.name.equals(m.name)) {

                List<LocalVariableNode> local_variables = method.localVariables;
                for (int l = 0; l < local_variables.size(); l++) {

                    String varName = local_variables.get(l).name;

                    int index = local_variables.get(l).index;

                    if (!"this".equals(varName)) {
                        varNames.add(new LocalVariable(index, varName));
                    }
                }
                LocalVariable[] tmpArr = varNames.toArray(new LocalVariable[varNames.size()]);

                Arrays.sort(tmpArr);
                for (int j = 0; j < m.size; j++) {
                    list.add(tmpArr[j].name);
                }
                break;

            }

        }
        return list;
    }


    static class LocalVariable implements Comparable<LocalVariable> {

        public int index;
        public String name;

        public LocalVariable(int index, String name) {
            this.index = index;
            this.name = name;
        }

        @Override
        public int compareTo(LocalVariable o) {
            return this.index - o.index;
        }
    }

    static class MethodMetadata {

        public String name;

        public String desc;

        public int size;

        public MethodMetadata(String name, String desc, int size) {
            this.name=name;
            this.desc = desc;
            this.size = size;
        }
    }

    public static void main(String[] args) throws IOException {
        for (Method m : StringUtils.class.getDeclaredMethods()) {
            List<String> list = getMethodParamNames(StringUtils.class, m);
            System.out.println(m.getName() + ":");
            for (String str : list) {
                System.out.println(str);
            }
            System.out.println("------------------------");
        }
    }
}
