#include <string>

#pragma once

class ArgParser {
private:
	std::vector<std::pair<std::string, std::shared_ptr<std::string>>> args;

public:
	ArgParser(int, char**);

	bool is_present(std::string&);

	bool is_present(std::vector<std::string>&&);

	std::shared_ptr<std::string> get_val(std::string&);

	std::shared_ptr<std::string> get_val(std::vector<std::string>&&);
};

bool equal(const char* str0, const char* str1);

bool starts_with(const char* str, const char* prefix);

bool ends_with(const char* str, const char* suffix);

size_t find_next(const char* str, const char* substr);

size_t find_last(const char* str, const char* substr);

char* clone(const char* str);

char* substring(const char* str, size_t from, size_t to);

char** split(const char* str, const char* delimeter, size_t*, size_t);
