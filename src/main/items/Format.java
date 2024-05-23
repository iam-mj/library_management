package items;

public enum Format {
    MP3, WMA, FLAC;

    public static Format stringToFormat(String s)
    {
        Format format = Format.MP3; // default
        if(s.compareTo("WMA") == 0)
            format = Format.WMA;
        if(s.compareTo("FLAC") == 0)
            format = Format.FLAC;
        return format;
    }
}
