package com.groupbcnc.pvp.infraestructure.controllers;

import java.time.LocalDateTime;
import java.util.Currency;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import com.groupbcnc.pvp.application.models.GetPvpCommand;
import com.groupbcnc.pvp.application.models.GetPvpCommandResult;
import com.groupbcnc.pvp.application.services.GetPvpUseCase;
import com.groupbcnc.pvp.domain.exceptions.ProductNotFoundException;
import com.groupbcnc.shared.domain.exceptions.ViolatedRuleException;

@Controller
public class GetPvpController {
    private final GetPvpUseCase useCase;

    public GetPvpController(GetPvpUseCase useCase) {
        this.useCase = useCase;
    }

    @GetMapping(value = "/{brandCode}/{productCode}/{currency}/pvp")
    public @ResponseBody ResponseEntity<GetPvpCommandResult> getPvp(
            @PathVariable UUID brandCode,
            @PathVariable UUID productCode,
            @PathVariable String currency) throws ProductNotFoundException, ViolatedRuleException {

        GetPvpCommand command = new GetPvpCommand(brandCode, productCode, Currency.getInstance(currency),
                LocalDateTime.now());

        GetPvpCommandResult commandResult = this.useCase.execute(command);

        return ResponseEntity.ok(commandResult);
    }
}
