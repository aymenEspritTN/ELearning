package com.deltaVelorum.coursify.chapitre;

import com.deltaVelorum.coursify.chapitre.entities.Chapitre;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreFile;
import com.deltaVelorum.coursify.chapitre.entities.ChapitreType;
import com.deltaVelorum.coursify.chapitre.services.ChapitreFileService;
import com.deltaVelorum.coursify.chapitre.services.ChapitreService;

public class Test {

    /// how to setup javaFX
    /// https://stackoverflow.com/questions/47224993/intellij-is-it-possible-to-make-controller-as-in-netbeans
    /// https://www.youtube.com/watch?v=hS_6ek9rTco
    public static void testAddFile()
    {
        ChapitreFile f = new ChapitreFile(132);
        f.setName("file.ext");
        f.setContentFromPath("C:/Users/aymen/Downloads/albert-dros-colorful-forest-landscape-4.jpg");
        f.setChapitreId(1);
        ChapitreFileService.getInstance().createTableIfNotExist();
        ChapitreFileService.getInstance().add(f);
    }
    public static void testAdd()
    {
        Chapitre c = new Chapitre();
        c.setName("hello");
        c.setDescription("this is a hello chapter.");
        c.setType(ChapitreType.Quizz);
        ChapitreService.getInstance().createTableIfNotExist();
        ChapitreService.getInstance().add(c);
    }
    public static void testUpdate(String x)
    {
        Chapitre c = new Chapitre();
        c.setId(1);
        c.setName("hello1111" + x);
        c.setDescription("this is a hello chapter." + x);
        ChapitreService.getInstance().update(c);
    }
    public static void testDelete()
    {
        ChapitreService.getInstance().delete(2);
    }
    public static void testGetAll()
    {
        System.out.println(ChapitreService.getInstance().getAll()); // test all
        System.out.println(ChapitreService.getInstance().getOne(3).getName()); // test one
    }
}
