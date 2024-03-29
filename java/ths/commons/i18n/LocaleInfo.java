package ths.commons.i18n;

import static ths.commons.lang.Assert.*;
import static ths.commons.util.StringUtils.*;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.UnsupportedCharsetException;
import java.util.Locale;

/**
 * 代表一个locale信息。
 * <p>
 * 即使charset不合法，本类也不会报错。请使用<code>LocaleUtil.isCharsetSupported()</code>
 * 方法来判断charset的合法性。
 * </p>
 * 
 */
public final class LocaleInfo implements Cloneable, Externalizable {
    private static final long serialVersionUID = 3257847675461251635L;
    private Locale locale;
    private Charset charset;

    /**
     * 解析并创建locale信息。
     * 
     * @param name locale信息的字符串，包含locale和charset信息，以“:”分隔
     * @return locale信息
     */
    public static LocaleInfo parse(String name) {
        name = assertNotNull(trimToNull(name), "no locale name");

        int index = name.indexOf(":");
        String localePart = name;
        String charsetPart = null;

        if (index >= 0) {
            localePart = name.substring(0, index);
            charsetPart = name.substring(index + 1);
        }

        Locale locale = LocaleUtils.parseLocale(localePart);
        String charset = trimToNull(charsetPart);

        return new LocaleInfo(locale, charset);
    }

    /**
     * 创建系统默认的locale信息。
     */
    public LocaleInfo() {
        this.locale = assertNotNull(Locale.getDefault(), "system locale");
        this.charset = assertNotNull(Charset.defaultCharset(), "system charset");
    }

    /**
     * 创建locale信息。
     * 
     * @param locale 区域信息
     */
    public LocaleInfo(Locale locale) {
        this(locale, null, LocaleUtils.getDefault());
    }

    /**
     * 创建locale信息。
     * 
     * @param locale 区域信息
     * @param charset 编码字符集
     */
    public LocaleInfo(Locale locale, String charset) {
        this(locale, charset, LocaleUtils.getDefault());
    }

    /**
     * 创建locale信息。
     * 
     * @param locale 区域信息
     * @param charset 编码字符集
     * @param fallbackLocaleInfo 上一级locale信息，如果未提供locale和charset，则从上一级中取得。
     */
    LocaleInfo(Locale locale, String charset, LocaleInfo fallbackLocaleInfo) {
        assertNotNull(fallbackLocaleInfo, "fallbackLocaleInfo");
        charset = trimToNull(charset);

        if (locale == null) {
            locale = fallbackLocaleInfo.getLocale();

            if (charset == null) {
                charset = fallbackLocaleInfo.getCharset().name();
            }
        } else {
            if (charset == null) {
                charset = "UTF-8"; // 如果指定了locale，但未指定charset，则使用万能的UTF-8
            }
        }

        this.locale = locale;

        try {
            this.charset = Charset.forName(charset);
        } catch (UnsupportedCharsetException e) {
            this.charset = new UnknownCharset(charset);
        }
    }

    /**
     * 取得区域。
     * 
     * @return 区域
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 取得编码字符集。
     * 
     * @return 编码字符集
     */
    public Charset getCharset() {
        return charset;
    }

    /**
     * 判断charset是否被支持。
     */
    public boolean isCharsetSupported() {
        return !(charset instanceof UnknownCharset);
    }

    /**
     * 判断charset是否被支持。
     */
    public LocaleInfo assertCharsetSupported() throws UnsupportedCharsetException {
        if (charset instanceof UnknownCharset) {
            throw new UnsupportedCharsetException(charset.name());
        }

        return this;
    }

    /**
     * 比较对象。
     * 
     * @param o 被比较的对象
     * @return 如果对象等效，则返回<code>true</code>
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (o == this) {
            return true;
        }

        if (!(o instanceof LocaleInfo)) {
            return false;
        }

        LocaleInfo otherLocaleInfo = (LocaleInfo) o;

        return locale.equals(otherLocaleInfo.locale) && charset.equals(otherLocaleInfo.charset);
    }

    @Override
    public int hashCode() {
        return charset.hashCode() * 31 + locale.hashCode();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            unexpectedException(e);
            return null;
        }
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(toString());
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        LocaleInfo info = parse(in.readUTF());

        locale = info.getLocale();
        charset = info.getCharset();
    }

    @Override
    public String toString() {
        return locale + ":" + charset;
    }

    /**
     * 代表一个不能识别的charset。
     */
    static class UnknownCharset extends Charset {
        public UnknownCharset(String name) {
            super(assertNotNull(name, "charset name"), null);
        }

        @Override
        public boolean contains(Charset cs) {
            return false;
        }

        @Override
        public CharsetDecoder newDecoder() {
            unsupportedOperation("Could not create decoder for unknown charset: " + name());
            return null;
        }

        @Override
        public CharsetEncoder newEncoder() {
            unsupportedOperation("Could not create encoder for unknown charset: " + name());
            return null;
        }
    }
}
