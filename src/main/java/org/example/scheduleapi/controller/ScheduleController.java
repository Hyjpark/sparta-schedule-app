package org.example.scheduleapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.scheduleapi.dto.ScheduleRequestDto;
import org.example.scheduleapi.dto.ScheduleResponseDto;
import org.example.scheduleapi.service.SchedulesService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedules")
@RequiredArgsConstructor
public class ScheduleController {

    private final SchedulesService schedulesService;

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(schedulesService.saveSchedule(requestDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> findAllSchedules(@RequestParam(required = false) String author) {
        return new ResponseEntity<>(schedulesService.findAllSchedules(author), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> findScheduleById(@PathVariable Long id) {
        return new ResponseEntity<>(schedulesService.findScheduleById(id), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        return new ResponseEntity<>(schedulesService.updateSchedule(id, requestDto.getTitle(), requestDto.getAuthor(), requestDto.getPassword()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        schedulesService.deleteSchedule(id, requestDto.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
