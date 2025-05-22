package challenge.web.action.test;

public class RegisterMVCResourceCommandTest {

  private RegisterMVCResourceCommand command;

    @Mock
    private ResourceRequest resourceRequest;

    @Mock
    private ResourceResponse resourceResponse;

    @Mock
    private RegistrationLocalService registrationLocalService;

    @Mock
    private CounterLocalService counterLocalService;

    private ByteArrayOutputStream responseStream;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        command = new RegisterMVCResourceCommand();

        command._registrationLocalService = registrationLocalService;
        command._counterLocalService = counterLocalService;

        responseStream = new ByteArrayOutputStream();
        when(resourceResponse.getPortletOutputStream()).thenReturn(new javax.portlet.PortletOutputStream() {
            @Override
            public void write(int b) {
                responseStream.write(b);
            }

            @Override
            public void flush() { }

            @Override
            public void close() { }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setWriteListener(javax.servlet.WriteListener writeListener) {}
        });

        JSONFactoryUtil jsonFactoryUtil = new JSONFactoryUtil();
        JSONFactory jsonFactory = jsonFactoryUtil.getJSONFactory();
        JSONFactoryUtil.setJSONFactory(jsonFactory);
    }

    @Test
    public void testServeResource_validInput() throws Exception {
        when(ParamUtil.getString(resourceRequest, "username")).thenReturn("Test User");
        when(ParamUtil.getString(resourceRequest, "email")).thenReturn("test@example.com");
        when(counterLocalService.increment()).thenReturn(1L);

        Registration registration = mock(Registration.class);
        when(registrationLocalService.createRegistration(1L)).thenReturn(registration);

        command.serveResource(resourceRequest, resourceResponse);

        String result = responseStream.toString("UTF-8");
        assert result.contains("\"success\":true");
    }

    @Test
    public void testServeResource_missingUsername() throws Exception {
        when(ParamUtil.getString(resourceRequest, "username")).thenReturn("");
        when(ParamUtil.getString(resourceRequest, "email")).thenReturn("test@example.com");

        command.serveResource(resourceRequest, resourceResponse);

        String result = responseStream.toString("UTF-8");
        assert result.contains("\"success\":false");
        assert result.contains("Name is required");
    }

}
