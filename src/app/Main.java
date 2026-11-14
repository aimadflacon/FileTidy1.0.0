package app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import entrada.Teclado;
import model.CategoryRule;
import model.ScanReport;
import service.DuplicateResolver;
import service.FileOrganizer;

public class Main {

	private static List<CategoryRule> createDefaultRules() {
		List<CategoryRule> rules = new ArrayList<>();

		// Regla para Imágenes
		rules.add(new CategoryRule("Imágenes")
				.addExtention("jpg")
				.addExtention("jpeg")
				.addExtention("png")
				.addExtention("gif")
				.addExtention("webp"));

		// Regla para Documentos
		rules.add(new CategoryRule("Documentos")
				.addExtention("pdf")
				.addExtention("docx")
				.addExtention("doc")
				.addExtention("txt")
				.addExtention("xlsx")
				.addExtention("pptx"));

		// Regla para Multimedia (Audio y Video)
		rules.add(new CategoryRule("Multimedia")
				.addExtention("mp4")
				.addExtention("mkv")
				.addExtention("mp3")
				.addExtention("wav"));

		// Regla para Archivos Comprimidos
		rules.add(new CategoryRule("Comprimidos")
				.addExtention("zip")
				.addExtention("rar")
				.addExtention("7z"));

		return rules;
	}

	private static void main() {
		System.out.println();
		System.out.println("-----------BIENVENIDO A FILETIDY-----------");
		System.out.println("[1] Organizar una carpeta");
		System.out.println("[2] ¿Cómo funciona FileTidy?");
		System.out.println("[3] Información de la aplicación");
		System.out.println("[4] Salir");
		System.out.println("-------------------------------------------");
		System.out.println();
	}

	private static void funcionamiento() {
		System.out.println();
		System.out.println( "              ¿CÓMO FUNCIONA FILETIDY?\n"
				+ "\n"
				+ "FileTidy es una herramienta diseñada para organizar\n"
				+ "automáticamente los archivos de tus carpetas de\n"
				+ "forma rápida, sencilla y segura.\n"
				+ "\n"
				+ "Su funcionamiento se divide en los siguientes pasos:\n"
				+ "\n"
				+ "1. SELECCIONAR DIRECTORIO\n"
				+ "   Introduce la ruta de la carpeta que deseas organizar.\n"
				+ "   Por ejemplo: Descargas, Escritorio o cualquier otra\n"
				+ "   carpeta de tu ordenador.\n"
				+ "\n"
				+ "2. ESCANEAR ARCHIVOS\n"
				+ "   FileTidy analizará los archivos del directorio\n"
				+ "   seleccionado e identificará su tipo mediante\n"
				+ "   sus extensiones.\n"
				+ "\n"
				+ "3. CLASIFICAR ARCHIVOS\n"
				+ "   Los archivos se agruparán automáticamente en\n"
				+ "   diferentes categorías:\n"
				+ "   - Imágenes\n"
				+ "   - Documentos\n"
				+ "   - Multimedia\n"
				+ "   - Otros archivos\n"
				+ "\n"
				+ "4. VISTA PREVIA\n"
				+ "   Antes de realizar cualquier cambio, podrás consultar\n"
				+ "   un resumen de los archivos que se van a organizar.\n"
				+ "\n"
				+ "5. CONFIRMAR ORGANIZACIÓN\n"
				+ "   Si estás de acuerdo con la clasificación, podrás\n"
				+ "   confirmar la operación para que FileTidy mueva\n"
				+ "   los archivos a sus correspondientes carpetas.\n"
				+ "\n"
				+ "6. PROTECCIÓN DE ARCHIVOS\n"
				+ "   FileTidy evita sobrescribir archivos existentes.\n"
				+ "   Si encuentra dos archivos con el mismo nombre,\n"
				+ "   generará automáticamente un nombre alternativo.\n"
				+ "\n"
				+ "¡Organiza tus archivos y mantén tus carpetas limpias\n"
				+ "con FileTidy!\n");
		System.out.println();
	}

	private static void informaciones() {
		System.out.println();
		System.out.println("                  INFORMACIÓN\n"
				+ "\n"
				+ "NOMBRE: FileTidy\n"
				+ "VERSIÓN: 1.0\n"
				+ "LENGUAJE: Java\n"
				+ "VERSIÓN MÍNIMA: Java 17\n"
				+ "PLATAFORMA: Multiplataforma\n"
				+ "\n"
				+ "DESCRIPCIÓN:\n"
				+ "\n"
				+ "FileTidy es una aplicación de consola desarrollada\n"
				+ "en Java que permite automatizar la organización\n"
				+ "de archivos mediante su clasificación por extensiones.\n"
				+ "\n"
				+ "Su objetivo es simplificar la gestión de directorios,\n"
				+ "reducir el desorden y facilitar el mantenimiento\n"
				+ "de los archivos personales.\n"
				+ "\n"
				+ "TECNOLOGÍAS UTILIZADAS:\n"
				+ "\n"
				+ "- Java 17+\n"
				+ "- Java NIO.2 (Gestión de archivos y directorios)\n"
				+ "- Java Streams (Procesamiento de archivos)\n"
				+ "- Programación orientada a objetos\n"
				+ "- Arquitectura en capas\n"
				+ "- Clean Architecture y principio SRP\n"
				+ "\n"
				+ "CARACTERÍSTICAS PRINCIPALES:\n"
				+ "\n"
				+ "- Escaneo automático de directorios.\n"
				+ "- Clasificación por extensiones.\n"
				+ "- Vista previa antes de organizar.\n"
				+ "- Protección contra sobrescritura de archivos.\n"
				+ "- Detección y resolución de nombres duplicados.\n"
				+ "- Compatible con Windows, macOS y Linux.\n"
				+ "\n"
				+ "             Gracias por utilizar FileTidy\n");
		System.out.println();
	}

	public static void main(String[] args) {
		DuplicateResolver duplicateResolver = new DuplicateResolver();
		FileOrganizer fileOrganizer = new FileOrganizer(duplicateResolver);
		ScanReport scanReport;
		List<CategoryRule> listaDeRules = createDefaultRules();
		int opcion;
		String ruta;
		String opcionDeOrganizar;


		do {
			main();
			opcion = Teclado.leerEntero("Seleccione una opción: ");
			switch(opcion) {
			case 1:
				ruta = Teclado.leerCadena("Introduzca la ruta del directorio que desea organizar: ");
				ruta = ruta.replaceAll("\"", "").replace("'", "");
				Path carpeta = Paths.get(ruta);

				try {
					ScanReport report = fileOrganizer.scanFolder(carpeta, listaDeRules);
					System.out.println("");
					System.out.println("  [INFO] Analizando el directorio seleccionado...");
					report.printPreview();
					System.out.println();

					if(report.TotalCategorizedCount() !=0) {
						System.out.println("¿Desea organizar los archivos de " + carpeta.getFileName() + "?"+" (s/n): ");
						opcionDeOrganizar = Teclado.leerCadena("");
						if(opcionDeOrganizar.equals("s") || opcionDeOrganizar.equals("si") || opcionDeOrganizar.equals("sí")) {
							System.out.println("  [INFO] Organizando archivos...");
							fileOrganizer.organizeFiles(report, listaDeRules);
							System.out.println("  [OK] ¡ORGANIZACIÓN COMPLETADA!"
									+ "Los archivos se han organizado correctamente.");
						}
						else {
							System.out.println("  [INFO] Operación Cancelada Por El Usuario. No Se Ha Modificado Ningún Archivo.");
						}
					}
				}catch (IllegalArgumentException e) {
					System.err.println("  [ERROR DE VALIDACIÓN] " + e.getMessage());
				} catch (IOException e) {
					System.err.println("  [ERROR DE SISTEMA DE ARCHIVOS] Ocurrió un error al procesar los archivos: " + e.getMessage());
				}
				break;
			case 2:
				funcionamiento();
				break;
			case 3:
				informaciones();
				break;
			case 4:
				System.out.println("-----------GRACIAS POR USAR FILETIDY-----------");
				System.out.println("   Mantén tus archivos siempre organizados ;)  ");
				System.out.println("-----------------------------------------------");
				opcion = 0;
				break;
			default:
				System.out.println("  [ERROR] Opción no válida. Seleccione una opción del 1 al 4.");
				break;
			}
		}while(opcion != 0);

	}

}

/*obserbaciones:
 * -ejecutando el programa en macbook sale archivos ignorados o archivos no categorizados uno mas de lo que hay en la carpeta, y es el archivo .DS_Store
 * que mac almacena pero es oculto y sin extensiones por eso lo detecta FileTidy y lo pasa a ignorados*/



















