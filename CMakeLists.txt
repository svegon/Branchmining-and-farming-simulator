cmake_minimum_required(VERSION 3.26)
project(Branchmining_and_Farming_Simulator)

set(CMAKE_CXX_STANDARD 23)

list(APPEND CMAKE_MODULE_PATH "${CMAKE_CURRENT_LIST_DIR}/cmake")

add_executable(Branchmining_and_Farming_Simulator
        src/client/client.cpp
        src/client/input.cpp
        src/util/tick_counter.cpp
        src/glad.c
        src/startup/run_args.cpp
        src/startup/arg_parse.cpp
        src/startup/main.cpp
        src/util/registry/registry.cpp
)

include(FindPkgConfig)
find_package(GLFW REQUIRED)
include_directories(${GLFW_INCLUDE})

find_package(glad REQUIRED)
include_directories(${GLAD_INCLUDE})

find_package(spdlog REQUIRED)
include_directories(${SPDLOG_INCLUDE})

find_package(rapidjson REQUIRED)
include_directories(${RAPIDJSON_INCLUDE})
