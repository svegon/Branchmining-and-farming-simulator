#include "tick_counter.h"

using namespace std;

namespace bafs {
	TickCounter::TickCounter(double tps, chrono::nanoseconds prev_start) {
		this->tps = tps;
		this->tick_length = chrono::nanoseconds((long)(1E9 / tps));
		this->prev_start = prev_start;
	}

    TickCounter::TickCounter(double tps)
    : TickCounter(tps, std::chrono::high_resolution_clock::now().time_since_epoch()) {}

	int TickCounter::begin_tick(chrono::nanoseconds tick_start) {
		last_frame_duration = tick_start - prev_start;
		prev_start = tick_start;
		tick_delta += last_frame_duration;
		int i = (int) (tick_delta.count() / tick_length.count());
		tick_delta -= chrono::nanoseconds(i * tick_length.count());
		return i;
	}

    int TickCounter::begin_tick() {
        return TickCounter::begin_tick(
                std::chrono::high_resolution_clock::now().time_since_epoch());
    }

	double TickCounter::get_tps() {
		return tps;
	}

	std::chrono::nanoseconds TickCounter::get_tick_delta() {
		return tick_delta;
	}

	std::chrono::nanoseconds TickCounter::get_last_frame_duration() {
		return last_frame_duration;
	}
}
