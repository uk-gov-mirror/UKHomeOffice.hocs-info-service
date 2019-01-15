package uk.gov.digital.ho.hocs.info.api;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import uk.gov.digital.ho.hocs.info.api.TopicService;
import uk.gov.digital.ho.hocs.info.client.caseworkclient.CaseworkClient;
import uk.gov.digital.ho.hocs.info.domain.model.ParentTopic;
import uk.gov.digital.ho.hocs.info.domain.model.Topic;
import uk.gov.digital.ho.hocs.info.domain.repository.ParentTopicRepository;
import uk.gov.digital.ho.hocs.info.domain.repository.TopicRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TopicServiceTest {

    @Mock
    private ParentTopicRepository parentTopicRepository;

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private CaseworkClient caseworkClient;

    private TopicService topicService;

    private UUID uuid = UUID.randomUUID();

    @Before
    public void setUp() {
        this.topicService = new TopicService(parentTopicRepository, topicRepository, caseworkClient);
    }

    @Test
    public void shouldReturnParentTopicsForCaseType() {
        when(parentTopicRepository.findAllParentTopicByCaseType(any())).thenReturn(getParentTopics());

        topicService.getParentTopics("MIN");

        verify(parentTopicRepository, times(1)).findAllParentTopicByCaseType(any());
        verifyNoMoreInteractions(parentTopicRepository);
    }

    @Test
    public void shouldReturnTopicsForParentTopic() {
        when(topicRepository.findTopicByParentTopic(uuid)).thenReturn(getTopics());

        topicService.getAllTopicsForParentTopic(uuid);

        verify(topicRepository, times(1)).findTopicByParentTopic(uuid);
        verifyNoMoreInteractions(parentTopicRepository);
    }

    @Test
    public void shouldReturnTopicsByCaseType() {
        when(topicRepository.findTopicByUUID(uuid)).thenReturn(new Topic(1l, "Topic1", UUID.randomUUID()));

        topicService.getTopic(uuid);

        verify(topicRepository, times(1)).findTopicByUUID(uuid);
        verifyNoMoreInteractions(parentTopicRepository);
    }

    private List<ParentTopic> getParentTopics() {
        return new ArrayList<ParentTopic>() {{
            add(new ParentTopic(1l, UUID.randomUUID(), "ParentTopic1", UUID.randomUUID(), UUID.randomUUID(), new HashSet<>()));
            add(new ParentTopic(2l, UUID.randomUUID(), "ParentTopic2", UUID.randomUUID(), UUID.randomUUID(), new HashSet<>()));
        }};

    }

    private List<Topic> getTopics() {
        return new ArrayList<Topic>() {{
            add(new Topic(1l, "Topic1", UUID.randomUUID()));
            add(new Topic(2l, "Topic2", UUID.randomUUID()));
        }};

    }
}