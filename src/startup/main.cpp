#include <filesystem>
#include <iostream>
#include <spdlog/spdlog.h>
#include <string>
#include <vector>

#include "arg_parse.h"
#include "run_args.h"

#ifdef _WIN32
#include <windows.h>
#endif

#include "main.h"

using namespace bafs;
using namespace std;

void parse_args(RunArgs& args, int argc, char** argv) {
	ArgParser parsed_args(argc, argv);

	shared_ptr<string> working_dir = parsed_args.get_val(
            vector<string>{string("--working-dir"), string("-d")});

	if (working_dir) {
		args.working_directory = filesystem::path(*working_dir);
	}
}

int main(int argc, char** argv) {
	RunArgs args;

	parse_args(args, argc, argv);

    shared_ptr<spdlog::logger> logger = spdlog::get("bafs");

    cout << "Starting Branchmining and farming simulator...";
	logger->debug("Starting Branchmining and farming simulator...");

	return 0;
}

#ifdef _WIN32
int WINAPI WinMain(HINSTANCE   hInstance,
                   HINSTANCE   hPrevInstance,
                   LPSTR       lpCmdLine,
                   int         nCmdShow) {
    size_t argc;
    char** argv = split(lpCmdLine, " ", &argc, nCmdShow);
    return main((int) argc, argv);
}
#endif
