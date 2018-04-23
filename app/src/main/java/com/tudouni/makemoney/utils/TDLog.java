package com.tudouni.makemoney.utils;

import android.support.annotation.NonNull;
import android.util.Log;

import com.tudouni.makemoney.BuildConfig;


/**
 * @author Jaron.Wu
 *         16-11-1.
 */
public class TDLog {
    private final static int NO_LOG = -1;
    private final static int LOG_V = Log.VERBOSE;
    private final static int LOG_D = Log.DEBUG;
    private final static int LOG_I = Log.INFO;
    private final static int LOG_W = Log.WARN;
    private final static int LOG_E = Log.ERROR;
    private static int logLevel = LOG_E;

    //对签名apk自动屏蔽日志
    static {
        logLevel = BuildConfig.DEBUG ? LOG_E : NO_LOG;
    }
    public static void initLog(boolean isDebug) {
        logLevel = isDebug ? LOG_E : NO_LOG;
    }

    public static void v(Object... objects) {
        if (logLevel >= LOG_V) {
            String argsToStr = varArgsToStr(objects);
            dividePrintMsg(argsToStr, LOG_V, generateTAG());
        }
    }

    @NonNull
    private static String varArgsToStr(Object... objects) {
        StackTraceElement[] trace = Thread.currentThread().getStackTrace();

        StringBuilder sb = new StringBuilder("╔════════════════════════════════════════════════════════════════════════════════════════════\n");
        int count = 0;
        for (Object object : objects) {
            count++;
            if (object instanceof String && ((String) object).startsWith("{")) {//TODO:不严格的判断，大括号开头的字符串视为Json
                sb.append(formatJson((String) object));
                sb.append('\n');
            } else {
                if (count == 1) {
//                    sb.append("║ 日志所在线程:   ");
//                    sb.append(Thread.currentThread().getName());
//                    sb.append('\n');
                    sb.append("╟──────────────────────────────────────────────────────────────────────────────────────────────────────\n║ 日志代码位置:   ");
                    sb.append(trace[4]);
                    sb.append("\n╟──────────────────────────────────────────────────────────────────────────────────────────────────────\n║ 日志内容:      ");
                } else {
                    sb.append("║               ");
                }
                sb.append(object == null ? "null" : object.toString());
                sb.append('\n');
            }
        }
        sb.append("╚════════════════════════════════════════════════════════════════════════════════════════════");
        return sb.toString();
    }

    public static void d(Object... objects) {
        if (logLevel >= LOG_D) {
            String argsToStr = varArgsToStr(objects);
            dividePrintMsg(argsToStr, LOG_D, generateTAG());
        }
    }

    public static void i(Object... objects) {
        if (logLevel >= LOG_I) {
            String argsToStr = varArgsToStr(objects);
            dividePrintMsg(argsToStr, LOG_I, generateTAG());
        }
    }

    public static void w(Object... objects) {
        if (logLevel >= LOG_W) {
            String argsToStr = varArgsToStr(objects);
            dividePrintMsg(argsToStr, LOG_W, generateTAG());
        }
    }

    public static void e(Object... objects) {
        if (logLevel >= LOG_E) {
            String argsToStr = varArgsToStr(objects);
            dividePrintMsg(argsToStr, LOG_E, generateTAG());
        }
    }

    /**
     * 分割要打印的日志内容（Android使用的日志工具单条日志只能打印4*1024个字符）
     *
     * @param argsToStr 要打印的内容
     * @param logLevel  使用的日志级别
     * @param Tag       打印日志使用的Tag
     */
    private static void dividePrintMsg(String argsToStr, int logLevel, String Tag) {
        if (argsToStr.length() > 4000) {
            for (int i = 0; i < argsToStr.length(); i += 4000) {
                if (i + 4000 < argsToStr.length()) {
                    if (i == 0) {
                        selectLogLevel(Tag, argsToStr, logLevel, i, "", i + 4000);
                    } else {
                        selectLogLevel(Tag, argsToStr, logLevel, i, "║                 ", i + 4000);
                    }
                } else {
                    selectLogLevel(Tag, argsToStr, logLevel, i, "║                 ", argsToStr.length());
                }
            }
        } else {
            selectLogLevel(Tag, argsToStr, logLevel, 0, "", argsToStr.length());
        }
    }

    /**
     * 选择日志级别
     *
     * @param Tag       tag
     * @param argsToStr 日志内容
     * @param logLevel  日志级别
     * @param i         分割时使用的startRegion
     * @param prefix    日志前缀，保持控制台日志对齐
     * @param subLength 分割时使用的endRegion
     */
    private static void selectLogLevel(String Tag, String argsToStr, int logLevel, int i, String prefix, int subLength) {
        switch (logLevel) {
            case LOG_E:
                Log.e(Tag, prefix + argsToStr.substring(i, subLength));
                break;
            case LOG_I:
                Log.i(Tag, prefix + argsToStr.substring(i, subLength));
                break;
            case LOG_D:
                Log.d(Tag, prefix + argsToStr.substring(i, subLength));
                break;
            case LOG_V:
                Log.v(Tag, prefix + argsToStr.substring(i, subLength));
                break;
            case LOG_W:
                Log.w(Tag, prefix + argsToStr.substring(i, subLength));
                break;
        }
    }

    /**
     * 生成日志Tag（使用类名）
     *
     * @return 返回tag
     */
    private static String generateTAG() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[4];
        String fullClassName = stackTrace.getClassName();
        return fullClassName.substring(fullClassName.lastIndexOf(".") + 1);
    }

    /**
     * 格式化JSON
     *
     * @param jsonStr 要打印的Json串
     * @return 返回格式化后的Json
     */
    private static String formatJson(String jsonStr) {
        if (null == jsonStr || "".equals(jsonStr)) return "";
        StringBuilder sb = new StringBuilder("║           ");
        char last;
        char current = '\0';
        int indent = 0;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '{':
                case '[':
                    sb.append(current);
                    sb.append("\n║           ");
                    indent++;
                    addIndentBlank(sb, indent);
                    break;
                case '}':
                case ']':
                    sb.append("\n║           ");
                    indent--;
                    addIndentBlank(sb, indent);
                    sb.append(current);
                    break;
                case ',':
                    sb.append(current);
                    if (last != '\\') {
                        sb.append("\n║           ");
                        addIndentBlank(sb, indent);
                    }
                    break;
                default:
                    sb.append(current);
            }
        }
        return sb.toString();
    }

    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }
}
