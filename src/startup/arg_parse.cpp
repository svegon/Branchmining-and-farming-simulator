#include <cstring>
#include <memory>
#include <stdexcept>
#include <string>
#include <vector>
#include "arg_parse.h"

using namespace std;

ArgParser::ArgParser(int argc, char** argv) {
	size_t n;

	for (int i = 0; i < argc; i++) {
		char* arg = argv[i];
		char** arg_and_value = split(arg, "=", &n, 2);

		switch (n) {
		case 1:
			args.push_back(pair<string, shared_ptr<string>>(string(arg), make_shared<string>()));
			break;
		case 2:
			args.push_back(pair<string, shared_ptr<string>>(string(arg_and_value[0]),
				make_shared<string>(string(arg_and_value[1]))));
			break;
		default:
			throw runtime_error(string("unexpected number of split results: " + to_string(n)));
		}
	}
}

bool ArgParser::is_present(string& arg_name) {
	for (pair<string, shared_ptr<string>>& p : args) {
		if (p.first == arg_name) {
			return true;
		}
	}

	return false;
}

bool ArgParser::is_present(std::vector<std::string>&& arg_names) {
	for (auto& arg_name : arg_names) {
		if (is_present(arg_name)) {
			return true;
		}
	}

	return false;
}

shared_ptr<string> ArgParser::get_val(string& arg_name) {
	for (pair<string, shared_ptr<string>>& p : args) {
		if (p.first == arg_name) {
			return p.second;
		}
	}

	return shared_ptr<string>();
}

shared_ptr<string> ArgParser::get_val(std::vector<std::string>&& arg_names) {
	for (auto& arg_name : arg_names) {
		std::shared_ptr<std::string> ptr = get_val(arg_name);

		if (ptr) {
			return ptr;
		}
	}

	return shared_ptr<string>();
}

bool equal(const char* str0, const char* str1) {
	return !strcmp(str0, str1);
}

bool starts_with(const char* str, const char* prefix) {
	return !strncmp(prefix, str, strlen(prefix));
}

bool ends_with(const char* str, const char* suffix) {
	size_t suffix_len = strlen(suffix);
	return !strncmp(str + strlen(str) - suffix_len, suffix, suffix_len);
}

size_t find_next(const char* str, const char* substr) {
	size_t len = strlen(substr);

	for (size_t i = 0; *str; i++) {
		if (!strncmp(str++, substr, len)) {
			return i;
		}
	}

	return -1;
}

size_t find_last(const char* str, const char* substr) {
	size_t len = strlen(substr);
	size_t i = strlen(str) - len;
	str += i++;

	while (i-- != 0) {
		if (!strncmp(str--, substr, len)) {
			return i;
		}
	}

	return -1;
}

char* clone(const char* str) {
	size_t len = strlen(str);
	char* result = new char[len];

	memcpy(result, str, len);

	return result;
}

char* substring(const char* str, size_t from, size_t to) {
	size_t len = to - from;
	char* result = new char[len + 1];

	strncpy(result, str + from, len);

	return result;
}

char** split(const char* str, const char* delimeter, size_t* size, size_t max_size = 0) {
	vector<char*> result;

	char* p = strtok((char*) str, delimeter);

	if (max_size <= 0) {
		do {
			result.push_back(p);
			p = strtok(nullptr, delimeter);
		} while (p);
	} else {
		do {
			result.push_back(p);
			p = strtok(nullptr, delimeter);
		} while (p && result.size() < max_size);
	}

	if (size) {
		*size = result.size();
	}

	return result.data();
}
