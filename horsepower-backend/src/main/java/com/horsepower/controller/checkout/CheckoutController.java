package com.horsepower.controller.checkout;

import com.horsepower.dto.checkout.CheckoutRequestDto;
import com.horsepower.dto.checkout.CheckoutResponseDto;
import com.horsepower.service.checkout.CheckoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService service;

    @PostMapping
    public CheckoutResponseDto create(@RequestBody @Valid CheckoutRequestDto dto) {
        return service.create(dto);
    }

    @GetMapping("/{userEmail}")
    public List<CheckoutResponseDto> getByUser(@PathVariable String userEmail) {
        return service.getByUser(userEmail);
    }

    @PutMapping("/{id}")
    public CheckoutResponseDto update(@PathVariable Long id,
                                      @RequestBody @Valid CheckoutRequestDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}

