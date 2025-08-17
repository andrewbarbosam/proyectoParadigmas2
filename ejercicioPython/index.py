import os  # Librería para manejar carpetas y archivos

# Función para contar líneas, palabras y caracteres de un archivo
def analizar_archivo(ruta_archivo):
    lineas = 0
    palabras = 0
    caracteres = 0

    with open(ruta_archivo, "r", encoding="utf-8") as f:
        for linea in f:
            lineas += 1
            palabras += len(linea.split())
            caracteres += len(linea)

    return lineas, palabras, caracteres


# Función para recorrer la carpeta y encontrar archivos .txt
def analizar_carpeta(ruta_carpeta):
    resultados = []

    for carpeta, subcarpetas, archivos in os.walk(ruta_carpeta):
        for archivo in archivos:
            if archivo.endswith(".txt"):
                ruta = os.path.join(carpeta, archivo)
                lineas, palabras, caracteres = analizar_archivo(ruta)
                resultados.append((ruta, lineas, palabras, caracteres))

    return resultados


# Función para imprimir la tabla de resultados
def imprimir_resultados(resultados):
    total_lineas = 0
    total_palabras = 0
    total_caracteres = 0

    print(f"{'File Name':<40} {'Lines':<10} {'Words':<10} {'Characters':<10}")
    print("-" * 70)

    for ruta, lineas, palabras, caracteres in resultados:
        print(f"{ruta:<40} {lineas:<10} {palabras:<10} {caracteres:<10}")
        total_lineas += lineas
        total_palabras += palabras
        total_caracteres += caracteres

    print("-" * 70)
    print(f"{'TOTAL':<40} {total_lineas:<10} {total_palabras:<10} {total_caracteres:<10}")


# Programa principal
if __name__ == "__main__":
    ruta = input("Escribe la ruta de la carpeta a analizar: ")
    resultados = analizar_carpeta(ruta)

    if resultados:
        imprimir_resultados(resultados)
    else:
        print("No se encontraron archivos .txt en la carpeta.")
