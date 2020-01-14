package org.geometerplus.android.fbreader.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.geometerplus.android.fbreader.FBReader;
import org.geometerplus.android.fbreader.libraryService.BookCollectionShadow;
import org.geometerplus.fbreader.book.Book;
import org.geometerplus.zlibrary.ui.android.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 图书内置打开页
 */
public class BookEnterActivity extends AppCompatActivity {

    private final BookCollectionShadow myCollection = new BookCollectionShadow();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_enter);

        openBookClick(null);
    }

    public void openBookClick(View view) {
        myCollection.bindToService(this, () -> {
            Book book = myCollection.getRecentBook(0);
            if (book == null) {
                String path = copy2Storage();
                System.out.println(path);
                book = myCollection.getBookByFile(path);
            }
            if (book != null) {
                FBReader.openBookActivity(BookEnterActivity.this, book, null);
                finish();
            } else {
                Toast.makeText(BookEnterActivity.this, "获取内置epub失败，请检查", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * @param assetsName 要复制的文件名
     * @param savePath   要保存的路径
     * @param saveName   复制后的文件名
     *                   testCopy(Context context)是一个测试例子。
     */
    public void copy(Context context, String assetsName, String savePath, String saveName) {
        String filename = savePath + "/" + saveName;
        File dir = new File(savePath);
        // 如果目录不中存在，创建这个目录
        if (!dir.exists())
            dir.mkdir();
        try {
            if (!(new File(filename)).exists()) {
                InputStream is = context.getResources().getAssets().open(assetsName);
                FileOutputStream fos = new FileOutputStream(filename);
                byte[] buffer = new byte[1024];
                int count;
                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 复制
     */
    public String copy2Storage() {
        String path = getFilesDir().getAbsolutePath();
        String name = "reader.epub";
        copy(this, name, path, name);
        return path + File.separator + name;
    }
}
