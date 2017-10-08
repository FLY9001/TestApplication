package android.content;
import android.annotation.Nullable;
import java.util.Map;
import java.util.Set;

//通过Context.getSharedPreferences(String name,int mode);
public interface SharedPreferences {
	//获取编辑器
    Editor edit();
    public interface Editor {
		//增、改
        Editor putString(String key, @Nullable String value);
        Editor putStringSet(String key, @Nullable Set<String> values);
        Editor putInt(String key, int value);
        Editor putLong(String key, long value);
        Editor putFloat(String key, float value);
        Editor putBoolean(String key, boolean value);
		//删
        Editor remove(String key);//单个
        Editor clear();//全部
		//提交
        boolean commit();
        void apply();//异步提交，节省资源，注意API版本
    }
	//查询
    Map<String, ?> getAll();
    @Nullable
    String getString(String key, @Nullable String defValue);
    @Nullable
    Set<String> getStringSet(String key, @Nullable Set<String> defValues);
    int getInt(String key, int defValue);
    long getLong(String key, long defValue);
    float getFloat(String key, float defValue);
    boolean getBoolean(String key, boolean defValue);
    boolean contains(String key);//查询是否存在

	//变化监听器
    void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener);
    void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener);
	public interface OnSharedPreferenceChangeListener {
        void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key);
    }
}
