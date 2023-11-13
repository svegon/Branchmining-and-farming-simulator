#pragma once

#include <chrono>

namespace bafs {
	class TickCounter {
	private:
		std::chrono::nanoseconds tick_delta;
		std::chrono::nanoseconds last_frame_duration;
		std::chrono::nanoseconds prev_start;
		std::chrono::nanoseconds tick_length;
		double tps;

	public:
		TickCounter(double tps, std::chrono::nanoseconds prev_start);

		explicit TickCounter(double tps);

		int begin_tick(std::chrono::nanoseconds tick_start);

		int begin_tick();

		double get_tps();

		std::chrono::nanoseconds get_tick_delta();

		std::chrono::nanoseconds get_last_frame_duration();
	};
}
