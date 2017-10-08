package android.content;
import android.annotation.Nullable;
import java.util.Map;
import java.util.Set;

//ͨ��Context.getSharedPreferences(String name,int mode);
public interface SharedPreferences {
	//��ȡ�༭��
    Editor edit();
    public interface Editor {
		//������
        Editor putString(String key, @Nullable String value);
        Editor putStringSet(String key, @Nullable Set<String> values);
        Editor putInt(String key, int value);
        Editor putLong(String key, long value);
        Editor putFloat(String key, float value);
        Editor putBoolean(String key, boolean value);
		//ɾ
        Editor remove(String key);//����
        Editor clear();//ȫ��
		//�ύ
        boolean commit();
        void apply();//�첽�ύ����ʡ��Դ��ע��API�汾
    }
	//��ѯ
    Map<String, ?> getAll();
    @Nullable
    String getString(String key, @Nullable String defValue);
    @Nullable
    Set<String> getStringSet(String key, @Nullable Set<String> defValues);
    int getInt(String key, int defValue);
    long getLong(String key, long defValue);
    float getFloat(String key, float defValue);
    boolean getBoolean(String key, boolean defValue);
    boolean contains(String key);//��ѯ�Ƿ����

	//�仯������
    void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener);
    void unregisterOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener listener);
	public interface OnSharedPreferenceChangeListener {
        void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key);
    }
}
