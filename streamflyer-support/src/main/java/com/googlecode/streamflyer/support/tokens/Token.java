package com.googlecode.streamflyer.support.tokens;

import java.util.regex.Pattern;

import com.googlecode.streamflyer.internal.thirdparty.ZzzValidate;
import com.googlecode.streamflyer.regex.MatchProcessor;
import com.googlecode.streamflyer.regex.ReplacingProcessor;
import com.googlecode.streamflyer.support.util.DoNothingProcessor;
import com.googlecode.streamflyer.support.util.EmbeddedFlagUtil;

/**
 * A token that shall be matched via a regular expression in a stream. Each token is associated with a
 * {@link MatchProcessor} which defines how a {@link TokenProcessor} shall process the matched token.
 * 
 * @author rwoo
 * 
 */
public class Token {

    /**
     * An ID for the token. The ID shall be unique among all tokens used with the {@link TokenProcessor}.
     */
    private String name;

    /**
     * This regular expression describes how a token can be matched. Flags should be embedded via
     * {@link EmbeddedFlagUtil}.
     */
    private String regex;

    /**
     * The number of capturing groups that are contained in the {@link #regex}.
     */
    private int capturingGroupCount;

    /**
     * This object processes the match if the token is matched.
     */
    private MatchProcessor matchProcessor;

    /**
     * This constructor should be used only in tests!
     * 
     * @param regex
     *            The regex describes how a token can be matched. Embed flags via {@link EmbeddedFlagUtil}.
     */
    public Token(String regex) {
        this("" + System.currentTimeMillis(), regex, new DoNothingProcessor());
    }

    /**
     * This token matches the given regex but the match processor does {@link DoNothingProcessor nothing}.
     * 
     * @param name
     *            See {@link #name}.
     * @param regex
     *            The regex describes how a token can be matched. Embed flags via {@link EmbeddedFlagUtil}.
     */
    public Token(String name, String regex) {
        this(name, regex, new DoNothingProcessor());
    }

    /**
     * This token matches the given regex and {@link ReplacingProcessor replaces} the match with the replacement.
     * 
     * @param name
     *            See {@link #name}.
     * @param regex
     *            The regex describes how a token can be matched. Embed flags via {@link EmbeddedFlagUtil}.
     * @param replacement
     */
    public Token(String name, String regex, String replacement) {
        this(name, regex, new ReplacingProcessor(replacement));
    }

    /**
     * This token matches the given regex and the match will be processed with the given {@link MatchProcessor}.
     * 
     * @param name
     *            See {@link #name}
     * @param regex
     *            The regex describes how a token can be matched. Embed flags via {@link EmbeddedFlagUtil}.
     * @param matchProcessor
     */
    public Token(String name, String regex, MatchProcessor matchProcessor) {
        super();

        ZzzValidate.notNull(matchProcessor, "matchProcessor must not be null");

        this.name = name;
        this.regex = regex;
        this.matchProcessor = matchProcessor;
        this.capturingGroupCount = Pattern.compile(regex).matcher("").groupCount();
    }

    public String getName() {
        return name;
    }

    public String getRegex() {
        return regex;
    }

    public int getCapturingGroupCount() {
        return capturingGroupCount;
    }

    public MatchProcessor getMatchProcessor() {
        return matchProcessor;
    }

    @Override
    public String toString() {
        return "Token [name=" + name + ", regex=" + regex + ", capturingGroupCount=" + capturingGroupCount
                + ", matchProcessor=" + matchProcessor + "]";
    }

}
