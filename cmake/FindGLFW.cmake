find_path(GLFW_INCLUDE glfw3.h
        PATH_SUFFIXES GLFW
        PATHS "C:\\Program Files\\JetBrains\\CLion 2023.2\\bin\\mingw\\include")

find_library(GLFW_LIB
        NAMES libglfw3
        PATHS "C:\\Program Files\\JetBrains\\CLion 2023.2\\bin\\mingw\\lib")
