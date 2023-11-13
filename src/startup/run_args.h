#pragma once

#include <filesystem>
#include <string>

namespace bafs {
    struct RunArgs {
        std::filesystem::path working_directory;

        RunArgs();
    };
}
