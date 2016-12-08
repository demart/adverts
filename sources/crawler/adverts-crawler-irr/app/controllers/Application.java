package controllers;

import play.*;
import play.mvc.*;

import java.util.*;

import models.*;

public class Application extends Controller {

    public static void index() {
    	
    	importData();
    	
        render();
    }

 
    private static void importData() {
    	// 1. Читаем регионы из БД
    	// 2. Сохраняем регионы в Монго + сохраняем ID
    	
    	
    	// 2. Читаем все регионы из кришы
    	// 3. Находим запись в Монго и ссылаемся на нее
    	// 4. Сохраняем запись с сылками
    	
    	
    	//Region
    	
    
    }
    
    
}