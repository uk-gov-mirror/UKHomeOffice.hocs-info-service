package uk.gov.digital.ho.hocs.info.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uk.gov.digital.ho.hocs.info.dto.GetMembersResponse;
import uk.gov.digital.ho.hocs.info.entities.Member;
import uk.gov.digital.ho.hocs.info.exception.EntityNotFoundException;

import java.util.Set;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Slf4j
@RestController
public class MemberResource {

    private final MemberService memberService;

    @Autowired
    public MemberResource(final MemberService memberService) {
        this.memberService = memberService;
    }

    @RequestMapping(value = "/members", method = RequestMethod.GET, produces = APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<GetMembersResponse> getAllMembers() {
        //try {
            Set<Member> members = memberService.getMembers();
            return ResponseEntity.ok(GetMembersResponse.from(members));
       // } catch (EntityNotFoundException e) {
        //    return ResponseEntity.badRequest().build();
       // }
    }
}
