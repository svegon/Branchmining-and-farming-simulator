#pragma once

#include <GLFW/glfw3.h>
#include <spdlog/spdlog.h>
#include "../startup/run_args.h"
#include "../util/tick_counter.h"
#include "../constants.h"

namespace bafs {
    class Client {
        private:
            std::shared_ptr<spdlog::logger> logger;

            //TickCounter tick_counter = TickCounter(TICK_SPEED);

        public:
            Client(RunArgs);
    };
}
