package uk.gov.digital.ho.hocs.info.member;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.digital.ho.hocs.info.RequestData;
import uk.gov.digital.ho.hocs.info.casetype.CaseTypeService;
import uk.gov.digital.ho.hocs.info.entities.Member;
import uk.gov.digital.ho.hocs.info.exception.EntityNotFoundException;
import uk.gov.digital.ho.hocs.info.exception.EntityPermissionException;
import uk.gov.digital.ho.hocs.info.exception.IngestException;
import uk.gov.digital.ho.hocs.info.house.ingest.ListConsumerService;
import uk.gov.digital.ho.hocs.info.repositories.MemberRepository;

import java.util.Set;
import java.util.UUID;

@Service
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final CaseTypeService caseTypeService;
    private final ListConsumerService listConsumerService;
    private final RequestData requestData;

    @Autowired
    public MemberService(MemberRepository memberRepository, CaseTypeService caseTypeService, ListConsumerService listConsumerService, RequestData requestData) {
        this.memberRepository = memberRepository;
        this.caseTypeService = caseTypeService;
        this.listConsumerService =listConsumerService;
        this.requestData = requestData;
    }

    public Member getMemberAndAddress(UUID uuid) throws EntityNotFoundException {
        log.debug("Requesting House Address for Member {}", uuid);
        Member member = memberRepository.findByUuid(uuid);
        if (member != null) {
            return member;
        } else {
            throw new EntityNotFoundException("Could not find member %s", uuid);
        }
    }

    public Set<Member> getAllActiveMembers(String caseType) throws EntityPermissionException {
        log.debug("Requesting all Members");
        if (caseTypeService.hasPermissionForCaseType(caseType)) {
            return  memberRepository.findAllActiveMembers();
        } else {
            throw new EntityPermissionException("Not allowed to get Members for CaseType, CaseType: %s not in Roles: %s", caseType, requestData.rolesString());
        }
    }

    public void updateWebMemberLists() throws IngestException {
        updateMember(listConsumerService.createFromWelshAssemblyAPI());
        updateMember(listConsumerService.createFromScottishParliamentAPI());
        updateMember(listConsumerService.createCommonsFromUKParliamentAPI());
        updateMember(listConsumerService.createLordsFromUKParliamentAPI());
//        updateMember(listConsumerService.createFromIrishAssemblyAPI());
//        updateMember(listConsumerService.createFromEuropeanParliamentAPI());
    }

    private void updateMember(Set<Member> members) {
        members.forEach(member -> {
            Member memberFromDB = memberRepository.findByExternalReference(member.getExternalReference());
            if (memberFromDB != null) {
                memberFromDB.update(member.getFullTitle());
                memberRepository.save(memberFromDB);
            } else {
                memberRepository.save(member);
            }
        });
    }
}
