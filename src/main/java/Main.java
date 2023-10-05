import com.DeltaVelorum.Coursify.Chapitre.Chapitre;
import com.DeltaVelorum.Coursify.Chapitre.ChapitreService;
import com.DeltaVelorum.Coursify.Chapitre.ChapitreType;
import com.DeltaVelorum.Coursify.Chapitre.DatabaseConnection;

import java.sql.Connection;

public class Main {
    public Connection cnx;
    public static void main(String[] args)
    {
        DatabaseConnection.getInstance().getCnx();
        //testUpdate("111111");
        //testGetAll();

        /*ChapitreFile f = new ChapitreFile();
        f.setName("file.ext");
        f.setContentFromPath("C:/Users/aymen/Downloads/albert-dros-colorful-forest-landscape-4.jpg");
        f.setChapitreId(1);
        ChapitreFileService.getInstance().createTableIfNotExist();
        ChapitreFileService.getInstance().add(f);*/
    }
    static void testAdd()
    {
        Chapitre c = new Chapitre();
        c.setName("hello");
        c.setDescription("this is a hello chapter.");
        c.setType(ChapitreType.Quizz);
        ChapitreService.getInstance().createTableIfNotExist();
        ChapitreService.getInstance().add(c);
    }
    static void testUpdate(String x)
    {
        Chapitre c = new Chapitre();
        c.setId(1);
        c.setName("hello1111" + x);
        c.setDescription("this is a hello chapter." + x);
        ChapitreService.getInstance().update(c);
    }
    static void testDelete()
    {
        ChapitreService.getInstance().delete(2);
    }
    static void testGetAll()
    {
        System.out.println(ChapitreService.getInstance().getAll()); // test all
        System.out.println(ChapitreService.getInstance().getOne(3).getName()); // test one
    }
}

