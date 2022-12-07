package enums;

public enum CharacterSet {
    GERMAN_UMLAUTS("ÄäÖöÜüß"),
    UMLAUTS("ÄäǞǟĄ̈ą̈B̈b̈C̈c̈ËëḦḧÏïḮḯJ̈j̈K̈k̈L̈l̈M̈m̈N̈n̈ÖöȪȫǪ̈ǫ̈ṎṏP̈p̈Q̈q̈Q̣̈q̣̈R̈r̈S̈s̈T̈ẗÜüǕǖǗǘǙǚǛǜṲṳṺṻṲ̄ṳ̄ᴞV̈v̈ẄẅẌẍŸÿZ̈z̈"),
    CHINESE_ALPHABET("请首先设置数据集项目属性。必须首先调用命令是数据集的部分时它是必需的无法为节点"),
    RUSSIAN_ALPHABET("АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдежзийклмнопрстуфхцчшщъыьэюя"),
    ENGLISH_ALPHABET("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvqxyz"),
    NUMERIC("0123456789"),
    SPECIAL_CHARS(",./?'|][{}()-_=+*&^:;#@$%\""),
    FORBIDDEN_CHARS(" АБВГДЕЖабвгдеж"),
    ALL_CHARS(ENGLISH_ALPHABET.getCharacters() + NUMERIC.getCharacters() + SPECIAL_CHARS.getCharacters());

    private String characters;

    CharacterSet(final String characters) {
        this.characters = characters;
    }

    public String getCharacters() {
        return characters;
    }
}
