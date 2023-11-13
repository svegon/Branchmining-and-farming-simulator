#include "run_args.h"

bafs::RunArgs::RunArgs() {
    working_directory = std::filesystem::current_path();
}
